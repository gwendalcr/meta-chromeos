SUMMARY = "Chrome OS Update Engine"
DESCRIPTION = "Chrome OS Update Engine"
HOMEPAGE = "https://chromium.googlesource.com/aosp/platform/system/update_engine/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=3b83ef96387f14655fc854ddc3c6bd57"

inherit chromeos_gn
PR = "r4145"

# TODO: puffin, bsdiff
DEPENDS:append = "\
    libbrillo \
    libchrome \
    protobuf \
    system-api \
"

CHROMEOS_PN = "update_engine"
S = "${WORKDIR}/src/platform2/${CHROMEOS_PN}"
B = "${WORKDIR}/build"
SRC_URI += "git://chromium.googlesource.com/aosp/platform/system/update_engine;protocol=https;branch=master;destsuffix=src/platform2/update_engine;name=chromiumos-platform-update_engine"
SRCREV_chromiumos-platform-update_engine = "b25fe63f16bd014a13114ffb6153cc5bd82b6728"
GN_ARGS += 'platform_subdir="${CHROMEOS_PN}"'

PACKAGECONFIG ??= ""

# Description of all the possible PACKAGECONFIG fields (comma delimited):
# 1. Extra arguments that should be added to the configure script argument list (EXTRA_OECONF or PACKAGECONFIG_CONFARGS) if the feature is enabled.
# 2. Extra arguments that should be added to EXTRA_OECONF or PACKAGECONFIG_CONFARGS if the feature is disabled.
# 3. Additional build dependencies (DEPENDS) that should be added if the feature is enabled.
# 4. Additional runtime dependencies (RDEPENDS) that should be added if the feature is enabled.
# 5. Additional runtime recommendations (RRECOMMENDS) that should be added if the feature is enabled.
# 6. Any conflicting (that is, mutually exclusive) PACKAGECONFIG settings for this feature.

# Empty PACKAGECONFIG options listed here to avoid warnings.
# The .bb file should use these to conditionally add patches,
# command-line switches and dependencies.
PACKAGECONFIG[cfm] = ""
PACKAGECONFIG[cros_host] = ""
PACKAGECONFIG[cros_p2p] = ""
PACKAGECONFIG[dlc] = ""
PACKAGECONFIG[fuzzer] = ""
PACKAGECONFIG[hw_details] = ""
PACKAGECONFIG[hwid_override] = ""
PACKAGECONFIG[minios] = ""
PACKAGECONFIG[power_management] = ""
PACKAGECONFIG[report_requisition] = ""
PACKAGECONFIG[systemd] = ""
PACKAGECONFIG[test] = ""

GN_ARGS += ' \
    use={ \
        cfm=${@bb.utils.contains('PACKAGECONFIG', 'cfm', 'true', 'false', d)} \
        cros_host=${@bb.utils.contains('PACKAGECONFIG', 'cros_host', 'true', 'false', d)} \
        cros_p2p=${@bb.utils.contains('PACKAGECONFIG', 'cros_p2p', 'true', 'false', d)} \
        dlc=${@bb.utils.contains('PACKAGECONFIG', 'dlc', 'true', 'false', d)} \
        fuzzer=${@bb.utils.contains('PACKAGECONFIG', 'fuzzer', 'true', 'false', d)} \
        hw_details=${@bb.utils.contains('PACKAGECONFIG', 'hw_details', 'true', 'false', d)} \
        hwid_override=${@bb.utils.contains('PACKAGECONFIG', 'hwid_override', 'true', 'false', d)} \
        minios=${@bb.utils.contains('PACKAGECONFIG', 'minios', 'true', 'false', d)} \
        power_management=${@bb.utils.contains('PACKAGECONFIG', 'power_management', 'true', 'false', d)} \
        report_requisition=${@bb.utils.contains('PACKAGECONFIG', 'report_requisition', 'true', 'false', d)} \
        systemd=${@bb.utils.contains('PACKAGECONFIG', 'systemd', 'true', 'false', d)} \
        test=${@bb.utils.contains('PACKAGECONFIG', 'test', 'true', 'false', d)} \
    } \
'

do_compile() {
    ninja -C ${B} ${CHROMEOS_PN}
}

do_install() {
    :
}

