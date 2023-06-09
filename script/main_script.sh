#!/bin/bash

chemin="cd ~/nas/Documment/2A/S4/R4.01/Projet/trace_de_rayon"
exec="java Noeud ip port"
for i in {224..245}
do
  ssh -o ConnectTimeout=1 -o StrictHostKeyChecking=accept-new 100.64.80.$i "${chemin} && ${exec} &" &
done
