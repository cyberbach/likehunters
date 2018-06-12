package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;

import net.overmy.likehunters.logic.CHARACTER_STATE;

/*
        Created by Andrey Mikheev on 12.06.2018
        Contact me → http://vk.com/id17317
*/
public class CharacterStateComponent implements Component {
    public CHARACTER_STATE state;
    public CHARACTER_STATE nextState;
}
