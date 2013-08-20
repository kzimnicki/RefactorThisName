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
import base64
import hashlib

TRANSLATED_SUBTITLE_FILE_NAME = "translatedSubtitle.srt"
PLUGIN_SUBTITLE_FILENAME = "temp_sub.en.srt"
PLUGIN_SUBTITLE_PATH = xbmc.translatePath('special://userdata/addon_data/script.xbmc.subtitles/sub_stream/temp_sub.en.srt')
TRANSLATING_TEXT = 'Translating...'
QUICK_TRANSLATE_URL ='https://explain.cc/app/quickSubtitleTranslate'
HEAD = 'Explain.CC'
TRANSLATED_SUBTITLE_FILENAME = xbmc.translatePath('special://userdata/addon_data/translatedSubtitle.srt')
EMPTY_STRING = ""


cachedSubtitle = ""

def isPluginSubtitles(subtitlesName):
    return PLUGIN_SUBTITLE_FILENAME == subtitlesName


def isTranslatedSubtitles(subtitlesName):
    return TRANSLATED_SUBTITLE_FILE_NAME == subtitlesName
	
def readFile(path) :
	if os.path.isfile(path) :
		f = open(path,'r')
		content = f.read()
		return content
	return ""

def writeFile(absoluteFilename, content) :
    f = open(absoluteFilename,'w')
    f.write(content)
    f.close()

def saveSubtitles(content) :
    print TRANSLATED_SUBTITLE_FILENAME
    writeFile(TRANSLATED_SUBTITLE_FILENAME, content)


def getSubtitleContentFromPluginFile() :
    return readFile(PLUGIN_SUBTITLE_PATH)

def getTranslatedSubtitleContent() :
    return readFile(TRANSLATED_SUBTITLE_FILENAME)

def quickSubtitleTranslate(subtitle) :
    popup = xbmcgui.DialogProgress()
    popup.create(HEAD, TRANSLATING_TEXT)
    response = EMPTY_STRING
    try:
        request = urllib2.Request(QUICK_TRANSLATE_URL)
        request.add_header("Authorization",getBasicAuthentication())
        params = urllib.urlencode({'subtitle': subtitle})
        response = urllib2.urlopen(request, params).read()
        print response;
    except Exception as inst:
        popup.close()
        xbmc.executebuiltin('Notification(Explain.CC,%s,20000)' % str(inst))
    popup.close()
    return response

def setTranslatedSubtitle(subtitle) :
    translatedSubtitle = quickSubtitleTranslate(subtitle)
    saveSubtitles(translatedSubtitle)
    player.setSubtitles(TRANSLATED_SUBTITLE_FILENAME)
    return translatedSubtitle

def saveSubtitleInCache(subtitle) :
    global cachedSubtitle
    cachedSubtitle = subtitle

def isUserAgreeToTranslateSubtitles() :
    dialog = xbmcgui.Dialog()
    return dialog.yesno(HEAD, 'Do you want translate subtitles ?')

def getBasicAuthentication() :
     addon = xbmcaddon.Addon()
     login = addon.getSetting('login')
     password = addon.getSetting('password')
     print hashlib.md5(login+password).hexdigest()
     auth = base64.encodestring('%s:%s' % (login, hashlib.md5(login+password).hexdigest())).replace('\n','')
     return 'Basic %s' % auth


def readSubtitle(subtitleName) :
    if isPluginSubtitles(subtitleName) :
        subtitle = getSubtitleContentFromPluginFile()
    elif isTranslatedSubtitles(subtitleName) :
        subtitle = getTranslatedSubtitleContent()
    else :
        subtitle = readFile(subtitleName)
    return subtitle

def isSubtitleChanged(subtitleName) :
    print len(readSubtitle(subtitleName))
    print len(cachedSubtitle)
    return readSubtitle(subtitleName) != cachedSubtitle

player = xbmc.Player()

while(1):
    time.sleep(2)
    if player.isPlayingVideo() and player.getSubtitles() :
        subtitleName = player.getSubtitles()
        if isSubtitleChanged(subtitleName) :
            subtitle = readSubtitle(subtitleName)
            if isUserAgreeToTranslateSubtitles() :
                subtitle = setTranslatedSubtitle(subtitle)
            saveSubtitleInCache(subtitle)