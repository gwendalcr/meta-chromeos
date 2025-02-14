SUMMARY = "BootLockbox DBus client library for Chromium OS"
DESCRIPTION = "BootLockbox DBus client library for Chromium OS"
HOMEPAGE = "https://chromium.googlesource.com/chromiumos/platform2/+/HEAD/cryptohome/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${CHROMEOS_COMMON_LICENSE_DIR}/BSD-Google;md5=29eff1da2c106782397de85224e6e6bc"

inherit chromeos_gn platform

CHROMEOS_PN = "cryptohome"

S = "${WORKDIR}/src/platform2/${CHROMEOS_PN}/${BPN}"
B = "${WORKDIR}/build"
PR = "r1861"

DEPENDS += "chromeos-dbus-bindings-native libbrillo libchrome protobuf-native"

GN_ARGS += 'platform_subdir="${CHROMEOS_PN}/${BPN}"'

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
PACKAGECONFIG[cros_host] = ""
PACKAGECONFIG[fuzzer] = ""
PACKAGECONFIG[profiling] = ""
PACKAGECONFIG[tcmalloc] = ""
PACKAGECONFIG[test] = ""

GN_ARGS += ' \
    use={ \
        cros_host=${@bb.utils.contains('PACKAGECONFIG', 'cros_host', 'true', 'false', d)} \
        fuzzer=${@bb.utils.contains('PACKAGECONFIG', 'fuzzer', 'true', 'false', d)} \
        profiling=${@bb.utils.contains('PACKAGECONFIG', 'profiling', 'true', 'false', d)} \
        tcmalloc=${@bb.utils.contains('PACKAGECONFIG', 'tcmalloc', 'true', 'false', d)} \
        test=${@bb.utils.contains('PACKAGECONFIG', 'test', 'true', 'false', d)} \
    } \
'

do_compile() {
    ninja -C ${B}
}

export SO_VERSION="1"

do_install() {
    # Export necessary header files:
    install -d ${D}${includedir}/${BPN}/bootlockbox
    install -m 0644 ${S}/../bootlockbox/boot_lockbox_client.h ${D}${includedir}/${BPN}/bootlockbox/

    # Export necessary for cryptohome header files:
    install -d ${D}${includedir}/cryptohome/bootlockbox
    install -m 0644 ${B}/gen/include/cryptohome/bootlockbox/*.h ${D}${includedir}/cryptohome/bootlockbox/

    install -d ${D}${libdir}
    install -m 0644 ${B}/libbootlockbox-proto.a ${D}${libdir}
    # Install libbootlockbox-client.so:
    find ${B}${base_libdir} -type f -name lib*.so | while read f; do
        fn=$(basename ${f})
        echo ${fn}
        install -m 0755 ${f} ${D}${libdir}/${fn}.${SO_VERSION}
        ln -sf ${fn}.${SO_VERSION} ${D}${libdir}/${fn}
    done

    platform_install_dbus_client_lib "bootlockbox"
}
