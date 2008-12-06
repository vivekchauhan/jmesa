
//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'Ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
// Ant.mkdir(dir:"/Volumes/work/projects/thirdpart/jmesa/branches/gsp/jmesa/grails-app/jobs")
//

Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
includeTargets << new File ( "${grailsHome}/scripts/Init.groovy" )

println "the jmesaPluginDir is ${jmesaPluginDir}"

pluginTemplatePath = "${jmesaPluginDir}/src/templates/"

overwrite = true

bind = [version:1.0]

generateFile = {templateFile, binding, outputPath ->
  def engine = new groovy.text.SimpleTemplateEngine()
  def templateF = new File(templateFile)
  def templateText = templateF.getText()
  def outFile = new File(outputPath)
  if (templateF.exists()) {
    if (overwrite) {
      def template = engine.createTemplate(templateText)
      //println template.make(binding).toString()
      outFile.withWriter {w ->
        template.make(binding).writeTo(w)
      }
      println "file generated at ${outFile.absolutePath}"
    } else {
      println "file *not* generated.: ${outFile.absolutePath} "
    }

  } else {
    println "${templateF} not exists"
  }
}

generateFile(
    "${pluginTemplatePath}/jmesa.properties",
    bind,
    "${basedir}/grails-app/conf/jmesa.properties")



