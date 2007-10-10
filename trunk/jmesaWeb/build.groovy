/* The JMesa build. Works best with Groovy 1.1. */
class Build {
    def ant = new AntBuilder()
    def ivy = new AntLibHelper(ant:ant, namespace:'fr.jayasoft.ivy.ant')
    
    def projectDir = '.'

    def resourcesDir = "${projectDir}/resources"
    def targetDir = "${projectDir}/target"

    def classesDir = "${targetDir}/classes"
    def libDir = "$targetDir/ivy/lib" 

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
    

    def war() {
        def warFile = "$targetDir/${artifact.name}-${artifact.revision}.war"
        ant.war(destfile:warFile, webxml:projectDir + '/web/WEB-INF/web.xml') {
            fileset(dir:"$projectDir/web")
            classes(dir:classesDir)
            lib(dir:"$targetDir/ivy/lib/master") {
                include(name:'*.jar')
            }
        }
        
        artifact.ext = 'war'
        artifact.file = warFile
    }
    
    def ivyresolve() {
        ivy.configure(file:"$projectDir/ivyconf.xml")
        ivy.resolve(file:"$projectDir/ivy.xml")
    }

    def ivyretrieve() {
        ivy.retrieve(pattern:"$libDir/[conf]/[artifact]-[revision].[ext]", sync:true)
    }
    
    def execute() {
        clean()
        init()
        ivyresolve()
        ivyretrieve()
        classpaths()
        compile()
        war()
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
