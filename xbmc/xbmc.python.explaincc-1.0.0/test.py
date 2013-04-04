import urllib
import urllib2


sub = ""

def quickSubtitleTranslate(subtitle) :
    params = urllib.urlencode({
      'subtitle': subtitle
    })
    response = urllib2.urlopen(QUICK_TRANSLATE_URL, params).read()
    return response

def readFileContent(path) :
    f = open(path,'r')
    content = f.read()
    return content

def writeFile(content) :
    f = open('test.txt','w')
    f.write(content)
    f.close()

def isSubtitleChanged(subtitle) :
    if subtitle == sub :
        return True
    else :
        return False




#c:\Users\kzimnick\AppData\Roaming\XBMC\userdata\addon_data\script.xbmc.subtitles\sub_stream\temp_sub.en.srt
#subtitle = readFileContent("c:/Users/kzimnick/AppData/Roaming/XBMC/userdata/addon_data/script.xbmc.subtitles/sub_stream/temp_sub.en.srt")
#print quickSubtitleTranslate(subtitle)
#print isSubtitleChanged("")

writeFile("Testdad adakdk a")


  