#!/bin/bash 

pwd

cd travis

# to encrypt
#openssl aes-256-cbc -a -salt -k "CRYPT_PASS" -in GooglePlay.p12 -out GooglePlay.p12.enc
#openssl aes-256-cbc -a -salt -k "CRYPT_PASS" -in release.keystore -out release.keystore.enc

openssl aes-256-cbc -a -d -k "$CRYPT_PASS" -in GooglePlay.p12.enc -out GooglePlay.p12
openssl aes-256-cbc -a -d -k "$CRYPT_PASS" -in release.keystore.enc -out release.keystore

cd ..
