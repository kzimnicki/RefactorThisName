# -*- coding: utf-8 -*- 

import os
import os.path
import sys
import xbmc
import xbmcgui
import xbmcaddon
import time
import urllib
import urllib2

QUICK_TRANSLATE_URL ='http://localhost:8080/RefactorThisName/app/quickSubtitleTranslate'
TRANSLATED_SUBTITLE_FILENAME = xbmc.translatePath('special://userdata/addon_data/translatedSubtitle.srt')

def isPluginSubtitles(subtitlesName):
    return "temp_sub.en.srt" == subtitlesName

def readFile(path) :
    f = open(path,'r')
    content = f.read()
    return content

def writeFile(absoluteFilename, content) :
    f = open(absoluteFilename,'w')
    f.write(content)
    f.close()

def saveSubtitles(content) :
    print TRANSLATED_SUBTITLE_FILENAME
    writeFile(TRANSLATED_SUBTITLE_FILENAME, content)


def getSubtitleContentFromPluginFile() :
    return readFile(xbmc.translatePath('special://userdata/addon_data/script.xbmc.subtitles/sub_stream/temp_sub.en.srt'))

def quickSubtitleTranslate(subtitle) :
    params = urllib.urlencode({
      'subtitle': subtitle
    })
    response = urllib2.urlopen(QUICK_TRANSLATE_URL, params).read()
    return response
def setTranslatedSubtitle(subtitle) :
    translatedSubtitle = quickSubtitleTranslate(subtitle)
    saveSubtitles(translatedSubtitle)
    player.setSubtitles(TRANSLATED_SUBTITLE_FILENAME)

def isUserAgreeToTranslateSubtitles() :
    dialog = xbmcgui.Dialog()
    return dialog.yesno('Explain.CC', 'Do you want translate subtitles ?')

player = xbmc.Player()
while(1):
    print "running"
    time.sleep(2)
    subtitleName = player.getSubtitles()
    if player.isPlayingVideo() and subtitleName :
        if isUserAgreeToTranslateSubtitles() :
            subtitle = ""
            if isPluginSubtitles(subtitleName) :
                print ">>Plugin "+subtitleName
                subtitle = getSubtitleContentFromPluginFile()
            else :
                print ">> File " + subtitleName
                subtitle = readFile(subtitleName)
            setTranslatedSubtitle(subtitle)
        break