ant = new AntBuilder()

ant.input(message:"Enter a build number:", addproperty:"version")
version = ant.antProject.properties."version"

src_dir = 'src'
lib_dir = 'lib'
resources_dir = 'resources'
build_dir = 'build'
classes_dir = build_dir + '/classes'
zip_dir = build_dir + "/jmesa-${version}"

ant.path(id:'classes') { fileset(dir:lib_dir) { include(name: "**/*.jar") } }

ant.delete(dir:build_dir)

ant.mkdir(dir:classes_dir)
ant.mkdir(dir:zip_dir + '/dist')
ant.mkdir(dir:zip_dir + '/images')
ant.mkdir(dir:zip_dir + '/source')

ant.echo(message:'You are using java version ${java.version}')

ant.javac(srcdir:src_dir, destdir:classes_dir, debug:false) { classpath(refid:'classes') }

ant.jar(jarfile:build_dir + "/jmesa-${version}.jar", basedir:classes_dir) { fileset(dir:src_dir) { include(name:'**/*.properties') }  }

ant.copy(todir:zip_dir + '/source') { fileset(dir:src_dir) }
ant.copy(todir:zip_dir + '/images') { fileset(dir:resources_dir + "/images") }
ant.copy(todir:zip_dir + '/dist', file:build_dir + "/jmesa-${version}.jar")
ant.copy(todir:zip_dir + '/dist', file:resources_dir + "/jmesa.js")
ant.copy(todir:zip_dir + '/dist', file:resources_dir + "/jmesa.css")
ant.copy(todir:zip_dir + '/dist', file:resources_dir + "/jmesa.tld")

ant.zip(zipfile:build_dir + "/jmesa-${version}.zip", basedir:zip_dir)

println "Done building jmesa-${version}.zip in ${build_dir} directory."

