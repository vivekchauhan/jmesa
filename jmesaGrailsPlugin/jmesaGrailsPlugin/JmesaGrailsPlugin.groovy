
class JmesaGrailsPlugin {
    def version = 0.8
    def dependsOn = [:]
	def author = "jeff.jie"
    def email = "bbmyth@gmail.com"

    def doWithWebDescriptor = { xml ->
        def contextParam = xml."context-param"
      contextParam[contextParam.size()-1]+{
        'context-param' {
          'param-name'('jmesaMessagesLocation')
          'param-value'('org/jmesa/core/message/jmesaResourceBundle')
        }
      }+{
        'context-param' {
          'param-name'('jmesaPreferencesLocation')
          'param-value'('/jmesa.properties')
        }
      }

    }

}
