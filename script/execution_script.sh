#!/bin/bash

for i in {224..245}
do
    ssh utilisateur@100.64.80.$i "java /chemin/vers/destination/programme" &
done

wait
