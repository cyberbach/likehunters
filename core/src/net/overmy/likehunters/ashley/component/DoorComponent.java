package net.overmy.likehunters.ashley.component;

import net.overmy.likehunters.logic.Item;

/*
      Created by Andrey Mikheev on 16.03.2018
      Contact me → http://vk.com/id17317
 */

public class DoorComponent extends ItemComponent {
    public float startAngle;
    public float endAngle;
    public float currentAngle;


    public DoorComponent ( Item itemForOpenDoor, float startAngle, float endAngle ) {
        super( itemForOpenDoor );

        this.startAngle = startAngle;
        this.currentAngle = startAngle;
        this.endAngle = endAngle;
    }
}
