# Upload a hash of the source folders and important build files
important_build_files="$(find resources -type f)
$(find src -type f)
$(find .settings -type f)
.classpath
.factorypath
.project
pom.xml"

shasum -a 256 $important_build_files