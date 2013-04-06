import urllib
import urllib2
import base64



def getBasicAuthentication() :
     auth = base64.encodestring('%s:%s' % ("user@gmail.com", "123456")).replace('\n','')
     return 'Basic %s' % auth


params = urllib.urlencode({
    'subtitle': "1234"
})

request = urllib2.Request('http://localhost:8080/RefactorThisName/app/quickSubtitleTranslate')
request.add_header("Authorization",getBasicAuthentication())
urllib2.urlopen(request, params)


  