package com.kanasansoft.ajaxmouse;

import java.util.HashMap;
import winstone.Launcher;

public class AjaxMouse {

	public static void main(String[] args) throws Exception {
		new AjaxMouse();
	}

	AjaxMouse() throws Exception{

		HashMap<String, String> prop = new HashMap<String, String>();
        prop.put("webroot", "war");
        prop.put("httpPort", "8080");
        prop.put("useJasper", "true");
        Launcher.initLogger(prop);

        final Launcher winstone = new Launcher(prop);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                winstone.shutdown();        
            }
        }));

	}

}
