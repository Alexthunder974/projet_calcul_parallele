#!/bin/bash

chemin="cd ~/Documents/projet_calcul_parallele/out/production/projet_calcul_parallele"
exec="java rmi.LancerNoeud 100.64.80.209 1099"
for i in {224..245}
do
  ssh -o ConnectTimeout=1 -o StrictHostKeyChecking=accept-new 100.64.80.$i "${chemin} && ${exec} &" &
done
