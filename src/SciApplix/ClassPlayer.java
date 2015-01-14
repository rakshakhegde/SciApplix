package SciApplix;

import Electromagnetism.ElectricField2;
import Electromagnetism.ElectricField;
import Electromagnetism.Magnetism;
import Mechanics.*;
import Fractals.*;
import Illusions.*;
import MathMarvels.*;
import Optics.*;
import Miscellaneous.*;
import java.io.DataInputStream;

/**
 * @author Rakshak.R.Hegde
 */
public class ClassPlayer {

    protected static String classes[][] = {{"Mechanics", "Envelope of Projectiles", "Gravitational Field", "Spring"},
        {"Optics", "Spherical Lens", "Anamorphic Art", "Interference"}, //  "Reflection"
        {"Fractals", "Sierpinski Triangle", "Fern Fractal", "Tree Fractal", "Ford's Circles", "Koch Fractal", "Sierpinski Triangle 2", "Ant Walk"},
        {"Math Marvels", "Prime Frequency Finder", "Numerical Integration"},
        {"Miscellaneous", "Spirograph", "Epicycloids", "Entropy", "Color Chooser", "Weight and Age On Planets", "Sunflower Seeds"},
        {"Electromagnetism", "Electric Field", "Electric Field 2", "Magnetism"},
        {"Illusions", "Moving Square"}};

    protected static String loadInfo(java.net.URL URL) {
        String info = "";
        try {
            DataInputStream input = new DataInputStream(URL.openStream());
            byte b[] = new byte[input.available()];
            input.read(b);
            info = new String(b);
            input.close();
            b = null;
        } catch (Exception ex) {
            info = "Information under construction!!! Please Google up the name of this module...";
            System.out.println(ex);
        }
        return info;
    }

    protected static void loadClass(final String category, final String className) {
        System.out.println("Category: " + category + "; Class Name: " + className);
        new Thread() {

            @Override
            public void run() {
                if (category.contentEquals(classes[0][0])) {
                    if (className.contentEquals(classes[0][1])) {
                        new EnvelopeOfProjectiles();
                    } else if (className.contentEquals(classes[0][2])) {
                        new GravitationalField();
                    } else if (className.contentEquals(classes[0][3])) {
                        new Spring();
                    }
                } else if (category.contentEquals(classes[1][0])) {
                    if (className.contentEquals(classes[1][1])) {
                        new SphericalLens();
                    } else if (className.contentEquals(classes[1][2])) {
                        new AnamorphicArt();
                    } else if (className.contentEquals(classes[1][3])) {
                        new Interference();
                    }
                } else if (category.contentEquals(classes[2][0])) {
                    if (className.contentEquals(classes[2][1])) {
                        new SierpinskiTriangle();
                    } else if (className.contentEquals(classes[2][2])) {
                        new FernFractal();
                    } else if (className.contentEquals(classes[2][3])) {
                        new TreeFractal();
                    } else if (className.contentEquals(classes[2][4])) {
                        new FordsCircles();
                    } else if (className.contentEquals(classes[2][5])) {
                        new KochFractal();
                    } else if (className.contentEquals(classes[2][6])) {
                        new SierpinskiTriangle2();
                    } else if (className.contentEquals(classes[2][7])) {
                        new AntWalk();
                    }
                } else if (category.contentEquals(classes[3][0])) {
                    if (className.contentEquals(classes[3][1])) {
                        new PrimeFrequency();
                    } else if (className.contentEquals(classes[3][2])) {
                        new NumericaIntegration();
                    }
                } else if (category.contentEquals(classes[4][0])) {
                    if (className.contentEquals(classes[4][1])) {
                        new Spirograph();
                    } else if (className.contentEquals(classes[4][2])) {
                        new Epicycloids();
                    } else if (className.contentEquals(classes[4][3])) {
                        new Entropy();
                    } else if (className.contentEquals(classes[4][4])) {
                        new ColorChooser();
                    } else if (className.contentEquals(classes[4][5])) {
                        new WeightOnPlanets();
                    } else if (className.contentEquals(classes[4][6])) {
                        new SunflowerSeeds();
                    }
                } else if (category.contentEquals(classes[5][0])) {
                    if (className.contentEquals(classes[5][1])) {
                        new ElectricField();
                    } else if (className.contentEquals(classes[5][2])) {
                        new ElectricField2();
                    } else if (className.contentEquals(classes[5][3])) {
                        new Magnetism();
                    }
                } else if (category.contentEquals(classes[6][0])) {
                    if (className.contentEquals(classes[6][1])) {
                        new Moving_Square();
                    }
                }
            }
        }.start();
    }
}