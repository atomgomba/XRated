#!/bin/bash
REPO_ZIP="/tmp/flags.zip"
EXTRACT_DIR="/tmp/currency-flags"
PWD=$(pwd)
RES_DIR="${PWD}/rates/src/main/res-transferwise"
DRAWABLE_DIR="${RES_DIR}/drawable"
wget https://github.com/transferwise/currency-flags/archive/master.zip -O ${REPO_ZIP}
rm -rf ${EXTRACT_DIR}
mkdir -p ${EXTRACT_DIR}
unzip -j ${REPO_ZIP} currency-flags-master/src/square-flags/*.svg currency-flags-master/LICENSE -d ${EXTRACT_DIR}
rm ${REPO_ZIP} ${DRAWABLE_DIR}/*.png
echo "Now converting SVG to PNG, please wait."
mogrify -format png -density 600 ${EXTRACT_DIR}/*.svg
echo "  * done."
cd ${EXTRACT_DIR}
rename 's/(.*)/currency_flag_$1/' *.png
mv currency_*.png ${DRAWABLE_DIR}
mv LICENSE ${RES_DIR}
cd ${PWD}
rm -rf ${EXTRACT_DIR}

