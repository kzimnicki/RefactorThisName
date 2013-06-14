import urllib
import urllib2
import base64



def getBasicAuthentication() :
     auth = base64.encodestring('%s:%s' % ("krzysztof@zimnicki.biz", "123456")).replace('\n','')
     return 'Basic %s' % auth


params = urllib.urlencode({
    'subtitle': """1
00:00:00,858 --> 00:00:03,006
<i>Previously</i>
<i>on</i> The Big Bang Theory...

54
00:02:54,928 --> 00:02:58,263
who, like me,
have been kept down by The Man."""
})

request = urllib2.Request('https://explain.cc/app/quickSubtitleTranslate')
print getBasicAuthentication()
#request.add_header("Authorization",getBasicAuthentication())
response = urllib2.urlopen(request, params)
print response.getcode()


  