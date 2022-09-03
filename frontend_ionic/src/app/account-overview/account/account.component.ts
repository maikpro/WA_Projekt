/* eslint-disable @typescript-eslint/member-ordering */
/* eslint-disable max-len */
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { KeycloakService } from 'keycloak-angular';
import { Account } from 'src/app/shared/models/Account';
import { KeycloakAccount } from 'src/app/shared/models/KeycloakAccount';
import { AccountService } from 'src/app/shared/services/account.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { environment } from 'src/environments/environment';


@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {
  public keycloakAccount!: KeycloakAccount;
  public accountForm!: FormGroup;
  public submitted = false;
  public isCreated = false;
  private username!: string;
  private account!: Account;

  constructor(
    private accountService: AccountService,
    private formBuilder: FormBuilder,
    private keycloakService: KeycloakService,
    private notifyService: NotificationService
    //private bestellService: BestellungService
  ) { }

  public ngOnInit(): void {
    this.accountForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', Validators.required],
      vorname: ['', Validators.required],
      nachname: ['', Validators.required],
      strasse: ['', Validators.required],
      hausnr: ['', Validators.required],
      plz: ['', Validators.required],
      ort: ['', Validators.required]
    });

     /**
      * wenn noch kein Account in myBay Datenbank vorhanden, dann muss der Account mit der Adresse angelegt werden.
      * */

    this.accountService.getCurrentAccountInfo().subscribe((account: Account) => {
      console.log(account);
      this.account=account;
      const adresse = account.adresse;
      this.accountForm.patchValue(account);
      this.accountForm.patchValue(adresse);
      this.isCreated = true;
      this.username = account.username;
    },
    error => {
      if(error.status === 404){
        console.log('Accountdaten ausfüllen...');
        this.submitted = true;

        // Befülle den Vor und Nachnamen aus den Keycloak Account
        this.accountService.getCurrentAccount().subscribe(res => {
          this.keycloakAccount = res;
          this.accountForm.patchValue({username: this.keycloakAccount.username});
          this.accountForm.patchValue({email: this.keycloakAccount.email});
          this.accountForm.patchValue({vorname: this.keycloakAccount.firstName});
          this.accountForm.patchValue({nachname: this.keycloakAccount.lastName});
          this.username = this.keycloakAccount.username;

          this.submitted = true;
        });

      }
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.accountForm.controls;
  }

  public save(): void {
    this.submitted = true;

    if(this.accountForm.valid){
      console.log(this.accountForm.value);
      const account: Account = {
        vorname: this.accountForm.value.vorname,
        nachname: this.accountForm.value.nachname,
        email: this.accountForm.value.email,
        username: this.accountForm.value.username,
        adresse: {
          strasse: this.accountForm.value.strasse,
          hausnr: this.accountForm.value.hausnr,
          plz: this.accountForm.value.plz,
          ort: this.accountForm.value.ort,
        },
        beobachtungsliste: []
      };
      this.accountService.createAccount(account).subscribe(res => {
        console.log(res);
        this.notifyService.showSuccessMessage('Der Account wurde in der myBay Datenbank angelegt!');
        this.submitted = false;
      });

    }
  }

  public update(): void {
    this.submitted = true;

    if(this.accountForm.valid){
      console.log(this.accountForm.value);
      const account: Account = {
        vorname: this.accountForm.value.vorname,
        nachname: this.accountForm.value.nachname,
        email: this.accountForm.value.email,
        username: this.accountForm.value.username,
        adresse: {
          strasse: this.accountForm.value.strasse,
          hausnr: this.accountForm.value.hausnr,
          plz: this.accountForm.value.plz,
          ort: this.accountForm.value.ort,
        },
        beobachtungsliste: this.account.beobachtungsliste
      };
      this.accountService.updateAccount(account).subscribe(res => {
        console.log(res);
        this.notifyService.showSuccessMessage('Deine Accountdaten wurden aktualisiert!');
        this.submitted = false;
      });

    }
  }

  public async delete() {
    const result = await this.notifyService.confirm('Account löschen', `Wollen Sie den Account mit dem Usernamen '${this.username}' wirklich löschen?`, 'Nein', 'Ja');
    console.log(result);

    if(result){
      this.accountService.deleteAccount().subscribe(res => {
        console.log(res);
        this.notifyService.showSuccessMessage('Du hast den Account gelöscht!');
        this.keycloakService.logout(environment + '/home');
      });
    }
  }

  public successMessageTest(): void{
    this.notifyService.showSuccessMessage('Eine Success Message!');
  }

  public errorMessageTest(): void {
    this.notifyService.showErrorMessage('Eine Error Message!');
  }

  public async testConfirmModal() {
    const result = await this.notifyService.confirm('Modaltitle', 'Modalmessage', 'Nein', 'Ja');
    console.log(result);
  }

  public async testWarningModal() {
    const result = await this.notifyService.confirm('Modaltitle', 'Modalmessage', 'Ok');
    console.log(result);
  }
}
