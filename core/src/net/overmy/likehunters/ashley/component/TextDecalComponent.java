package net.overmy.likehunters.ashley.component;


import net.overmy.likehunters.resources.TextAsset;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class TextDecalComponent extends TimeComponent {

    public final TextAsset text;


    public TextDecalComponent ( TextAsset text, float time ) {
        this.text = text;
        this.time = time;
    }
}
