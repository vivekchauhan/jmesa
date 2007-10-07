/* The JMesa build. */
class Build {
    def ant = new AntBuilder()
    def ivy = new AntLibHelper(ant:ant, namespace:'fr.jayasoft.ivy.ant')
    
    def projectDir = '.'

    def resourcesDir = "${projectDir}/resources"
    def targetDir = "${projectDir}/target"
    def classesDir = "${targetDir}/classes"
    def libDir = "$targetDir/ivy/lib" 
    def distDir = "${targetDir}/dist"
    def docsDir = "${targetDir}/docs"
    
    def artifact
    def zipDir
    
    def sourceFilesTocopy = '**/*.properties,**/*.xml'
    
    Build(clean) {
        if (!clean) {
            this.artifact = ['jmesa'] as Artifact
            ant.input(message:'Enter a revision number:', addproperty:'revision', defaultvalue:'snapshot')
            artifact.revision = ant.antProject.properties."revision"
            this.zipDir = "${distDir}/${artifact.name}-${artifact.revision}"
        }
    }
    
    def clean() {
        ant.delete(dir:targetDir)
    }
    
    def init() {
        ant.mkdir(dir:targetDir)
        ant.mkdir(dir:"$libDir/compile")
        ant.mkdir(dir:classesDir)
        ant.mkdir(dir:docsDir)
        ant.mkdir(dir:zipDir)
        ant.mkdir(dir:"${zipDir}/dist")
        ant.mkdir(dir:"${zipDir}/images")
        ant.mkdir(dir:"${zipDir}/source")
    }
    
    def classpaths() {
        ant.path(id:'compile.classpath') {
            fileset(dir:"$libDir/compile", includes:'*.jar')
        }
    }
    
    def compile() {
        ant.echo(message:'You are using java version ${java.version}')

        ant.javac(destdir:classesDir, srcdir:"$projectDir/src", debug:false, includeantruntime:false) {
            classpath(refid:'compile.classpath')
        }
        
        ant.copy(todir:classesDir) {
            fileset(dir:"$projectDir/src", includes:sourceFilesTocopy)
        }
    }
    
    def jar() {
        def jarFile = "$targetDir/${artifact.name}-${artifact.revision}.jar"
        ant.jar(destfile:jarFile) {
            fileset(dir:classesDir)
        }
        
        artifact.ext = 'jar'
        artifact.file = jarFile
    }
    
    def copy() {
        ant.copy(todir:zipDir + '/source') { fileset(dir:'src') }
        ant.copy(todir:zipDir + '/images') { fileset(dir:resourcesDir + '/images') }
        ant.copy(todir:zipDir + '/dist', file:targetDir + "/${artifact.name}-${artifact.revision}.jar")
        ant.copy(todir:zipDir + '/dist', file:resourcesDir + '/jmesa.js')
        ant.copy(todir:zipDir + '/dist', file:resourcesDir + '/jmesa.css')
        ant.copy(todir:zipDir + '/dist', file:resourcesDir + '/jmesa.tld')
    }
    
    def zip() {
        ant.zip(destfile:targetDir + "/${artifact.name}-${artifact.revision}.zip", basedir:distDir)
    }
    
    def ivyresolve() {
        ivy.configure(file:"$projectDir/ivyconf.xml")
        ivy.resolve(file:"$projectDir/ivy.xml")
    }

    def ivyretrieve() {
        ivy.retrieve(pattern:"$libDir/[conf]/[artifact]-[revision].[ext]", sync:true)
    }
    
    def ivypublish() {
        ivy.publish(resolver:'local',
                conf:'master',
                pubrevision:"${artifact.revision}",
                overwrite:'true',
                artifactspattern:"${targetDir}/[artifact]-[revision].[ext]")
    }
    
    def docs() {
        ant.javadoc(sourcepath:'src',
                destdir:docsDir,
                windowtitle:'JMesa',
                additionalparam:'-breakiterator',
                source:"1.5",
                linksource:"true",
                access:"package",
                author:"true",
                version:"true",
                use:"true",
                defaultexcludes:"true") {
            doctitle('<![CDATA[<h1>JMesa</h1>]]>')
            classpath(refid:"compile.classpath")
            packageset(dir:"src") {
                include(name:"org/jmesa/**")
            }
            link(href:"http://java.sun.com/j2se/1.5.0/docs/api")
        }
    }
    
    def execute() {
        clean()
        init()
        ivyresolve()
        ivyretrieve()
        classpaths()
        compile()
        jar()
        copy()
        zip()
        docs()
    }
    
    static void main(args) {
        def cli = new CliBuilder(usage:'groovy build.groovy -[ha]')
        cli.h(longOpt: 'help', 'usage information')
        cli.a(argName:'action', longOpt:'action', args:1, required:true, 'action(s) [execute, clean]')
        
        def options = cli.parse(args)
        
        if (options.h) {
            cli.usage()
            return
        }

        def build = new Build(options.a == 'clean')
        def action = options.a
        build.invokeMethod(action, null)
    }
}

class AntLibHelper {
    def namespace 
    def ant
    
    Object invokeMethod(String name, Object params) {
        ant."antlib:$namespace:$name"(*params)
    }
}

class Artifact {
    def name
    def revision
    def ext
    def file
    
    Artifact(name) {
        this.name = name
    }
}
