/* The JMesa build. Works best with Groovy 1.1. */
class Build {
    def ant = new AntBuilder()
    def ivy = new AntLibHelper(ant:ant, namespace:'fr.jayasoft.ivy.ant')
    
    def projectDir = '.'

    def resourcesDir = "${projectDir}/resources"
    def targetDir = "${projectDir}/target"

    def classesDir = "${targetDir}/classes"
    def libDir = "$targetDir/ivy/lib"
    
    def sourceDir = "$targetDir/source"

    def artifact
    
    def sourceFilesTocopy = '**/*.properties,**/*.xml'
    
    Build(clean) {
        if (!clean) {
            this.artifact = ['jmesa'] as Artifact
            ant.input(message:'Enter a revision number:', addproperty:'revision', defaultvalue:'snapshot')
            artifact.revision = ant.antProject.properties."revision"
        }
    }
    
    def clean() {
        ant.delete(dir:targetDir)
    }
    
    def init() {
        ant.mkdir(dir:targetDir)
        ant.mkdir(dir:classesDir)
        ant.delete(dir:System.getProperty('user.home') + '/.ivy/cache/jmesa')
    }
    
    def classpaths() {
        ant.path(id:'compile.classpath') {
            fileset(dir:"$libDir/compile", includes:'*.jar')
        }
    }
    
    def compile() {
        ant.echo(message:'You are using java version ${java.version}')

        ant.javac(destdir:classesDir, srcdir:"$projectDir/src", debug:false, source:'1.5', target:'1.5', includeantruntime:false) {
            classpath(refid:'compile.classpath')
        }
        
        ant.copy(todir:classesDir) {
            fileset(dir:"$projectDir/src", includes:sourceFilesTocopy)
        }
    }
    

    def war() {
        def warFile = "$targetDir/${artifact.name}-${artifact.revision}_examples.war"
        ant.war(destfile:warFile, webxml:projectDir + '/web/WEB-INF/web.xml', duplicate:'preserve') {
            fileset(dir:"$projectDir/web")
            classes(dir:classesDir)
            lib(dir:"$targetDir/ivy/lib/master") {
                include(name:'*.jar')
            }
        }
        
        artifact.ext = 'war'
        artifact.file = warFile
    }
    
    def source() {
        ant.mkdir(dir:sourceDir)
        ant.mkdir(dir:"${sourceDir}/jmesaWeb")
        ant.mkdir(dir:"${sourceDir}/jmesaWeb/src")
        ant.mkdir(dir:"${sourceDir}/jmesaWeb/web")
        
        ant.copy(todir:"${sourceDir}/jmesaWeb/src") { fileset(dir:'src') }
        ant.copy(todir:"${sourceDir}/jmesaWeb/web") { 
            fileset(dir:'web', excludes:"**/classes/**", excludes:"**/lib/**") 
        }
        
        ant.zip(destfile:targetDir + "/${artifact.name}-${artifact.revision}_examples.war-source.zip", basedir:sourceDir)
    }
    
    def ivyresolve() {
        ivy.configure(file:"$projectDir/ivyconf.xml")
        ivy.resolve(file:"$projectDir/ivy.xml")
    }

    def ivyretrieve() {
        ivy.retrieve(pattern:"$libDir/[conf]/[artifact]-[revision].[ext]", sync:true)
    }
    
    /*
     * Retrieve the dependant jars from the ivy repository and put 
     * them in the lib folder of the project
     */
    def lib() {
        def libDir = 'web/WEB-INF/lib'
        ant.mkdir(dir:libDir)
        ivy.configure(file:'ivyconf.xml')
        ivy.resolve(file:'ivy.xml')
        ivy.retrieve(pattern:"$libDir/[artifact]-[revision].[ext]", sync:true, conf:'ide')
    }    
    
    def dist() {
        clean()
        init()
        ivyresolve()
        ivyretrieve()
        classpaths()
        compile()
        war()
        source()
    }
    
    static void main(args) {
        def cli = new CliBuilder(usage:'groovy build.groovy -[ha]')
        cli.h(longOpt: 'help', 'usage information')
        cli.a(argName:'action', longOpt:'action', args:1, required:true, 'action(s) [dist, clean, lib]')
        
        def options = cli.parse(args)
        
        if (options.h) {
            cli.usage()
            return
        }

        def build = new Build(options.a == 'clean' || options.a == 'lib')
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
