#!/bin/bash

sfctl application delete --application-id QDPP
sfctl application unprovision --application-type-name QDPPType --application-type-version 1.0.0
sfctl store delete --content-path QDPP
