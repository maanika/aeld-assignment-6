# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/maanika/aeld-assignments-3-and-later.git;protocol=ssh;branch=main"

PV = "1.0+git${SRCPV}"
SRCREV = "e4f8bb01ed82df743b94efe841c8d9f5249e3c96"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

# customize these as necessary for any libraries you need for your application
TARGET_LDFLAGS += "-pthread -lrt"

# Start Script implementation
inherit update-rc.d

# Flag that the package uses init scripts
INITSCRIPT_PACKAGES = "${PN}"

INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"

# add the aesdsocket application and any other files you need to install
# see https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/aesdsocket" 
FILES:${PN} += "${sysconfdir}/init.d/aesdsocket-start-stop"

RDEPENDS:${PN} += "libgcc"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d
}
