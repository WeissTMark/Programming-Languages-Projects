package Basic;

import java.text.DecimalFormat;

/**
 * General class for a lightbulb
 */
public class JavaLightBulb {
    private double usage;
    private double totalPower = 0;
    private double lifetime = 10;
    public final static double LIFE_PER_USE = 0.3;

    /**
     * Constructor that requires the power draw. Default lifetime is 100
     * @param usage the amount of power per unit of time
     */
    public JavaLightBulb(double usage){
        this.usage = usage;
        System.out.println("base");
    }

    /**
     * Constructor that requires  the power draw and how long the light should last
     * @param usage the amount of power per unit of time
     * @param lifetime how long the lightbulb should last
     */
    public JavaLightBulb(double usage, double lifetime){
        this.usage = usage;
        this.lifetime = lifetime;
    }

    /**
     * Adds the total power consumption to the lightbulb for the given time period and
     * decreases the lifetime by one.
     * @param time how long the light is on
     */
    public void turnOn(double time){

        if(isAlive()) {
            totalPower += time * usage;
            lifetime -= time * usage * LIFE_PER_USE;

            if(!isAlive()) {
                lifetime = 0;
            }
        }
    }


    /**
     * returns true if the light can still turn on
     * @return if their is life left is the bulb, this returns true
     */
    public boolean isAlive(){
        return lifetime>0;
    }

    /**
     * Returns the status of the light
     * @return the status of the light
     */
    public String toString(){
        DecimalFormat format = new DecimalFormat("#.##");
        return "The lightbulb has drawn " + format.format(totalPower) + " worth of power and has " + format.format(lifetime)  + " life left.";
    }
}