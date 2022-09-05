#!/bin/bash

# android ordner löschen:
#rm -r android

# www Ordner erstellen:
#ionic build --configuration=production

# add android Platform
#npx cap add android

#npx cap sync android

# in android studio öffnen:
#npx cap open android

# oder
#ionic capacitor copy android
#ionic capacitor build android --configuration=production 
#ionic capacitor run android -l --external
#ionic cap run android -l --external --target=Pixel_3_API_30 --public-host=192.168.178.20 --configuration=production 

################################################
################################################
################################################

# www Ordner erstellen:
ionic build 

# Android Plattform hinzufügen
#ionic cap add android

# Syncing
#ionic cap sync android

# open App in Androidstudio
#ionic cap open android
#ionic cap run android -l --external --target=Pixel_3_API_30 --public-host=192.168.178.20 --configuration=production 

ionic cap run android -l --external --target=Pixel_3_API_30 --host=192.168.178.20 --port=8100  --public-host=192.168.178.20 --open