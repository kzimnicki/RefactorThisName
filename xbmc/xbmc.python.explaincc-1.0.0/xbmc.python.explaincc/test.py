import urllib
import urllib2
import base64
import hashlib



def getBasicAuthentication() :
     auth = base64.encodestring('%s:%s' % ("", "")).replace('\n','')
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

#request = urllib2.Request('https://explain.cc/app/quickSubtitleTranslate')
#print getBasicAuthentication()
##request.add_header("Authorization",getBasicAuthentication())
#response = urllib2.urlopen(request, params)
#print response.getcode()


print hashlib.md5("whatever your string is").hexdigest()


  