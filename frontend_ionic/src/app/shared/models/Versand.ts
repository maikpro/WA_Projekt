import { LaenderCode } from "./LaenderCode";
import { Lieferant } from "./Lieferant";

export interface Versand {
    id?: number;
    kosten: number;
    lieferant: Lieferant;
    laenderCode: LaenderCode;
}