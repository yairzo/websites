#!/bin/bash

rsync -avv $HOME/mop/tomcat/webapps/iws.war tomcat@ard-new.ard.huji.ac.il:/home/tomcat/tmp
