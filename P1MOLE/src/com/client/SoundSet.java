package com.client;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
public class SoundSet {
	AudioClip clip1, clip2, clip3;
	public void SoundSet(){
		 try {
             File file = new File("sound/HAMMER.AU"); 
             clip1 = Applet.newAudioClip(file.toURL());
            
         } catch (MalformedURLException e){
             e.printStackTrace();
         }
		 
		 try {
             File file = new File("sound/HIT.AU"); 
             clip2 = Applet.newAudioClip(file.toURL());
            
         } catch (MalformedURLException e){
             e.printStackTrace();
         }
		 
		 try {
             File file = new File("sound/UP.AU"); 
             clip3 = Applet.newAudioClip(file.toURL());
            
         } catch (MalformedURLException e){
             e.printStackTrace();
         }
	}
}
