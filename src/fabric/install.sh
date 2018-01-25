#!/bin/bash

sfctl application upload --path QDPP --show-progress
sfctl application provision --application-type-build-path QDPP
sfctl application create --app-name fabric:/QDPP --app-type QDPPType --app-version 1.0.0
