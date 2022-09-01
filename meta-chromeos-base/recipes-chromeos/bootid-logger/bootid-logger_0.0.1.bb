SUMMARY = "Program to record the current boot ID to the log"
DESCRIPTION = "Program to record the current boot ID to the log"
HOMEPAGE = "https://chromium.googlesource.com/chromiumos/platform2/+/HEAD/bootid-logger"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${CHROMEOS_COMMON_LICENSE_DIR}/BSD-Google;md5=29eff1da2c106782397de85224e6e6bc"

inherit chromeos_gn

S = "${WORKDIR}/src/platform2/${BPN}"
B = "${WORKDIR}/build"
PR = "r203"

GN_ARGS += 'platform_subdir="${BPN}"'


do_compile() {
    ninja -C ${B}
}

do_install() {
    :
}

