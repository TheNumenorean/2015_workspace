package org.usfirst.frc.team2984.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.image.RGBImage;

/**
 * This is a demo program showing the use of the NIVision class to do vision processing. 
 * The image is acquired from the USB Webcam, then a circle is overlayed on it. 
 * The NIVision class supplies dozens of methods for different types of processing. 
 * The resulting image can then be sent to the FRC PC Dashboard with setImage()
 */
public class Robot extends SampleRobot {
    int session;
    int session2;
    RGBImage rgbFrame;
    Image frame;

    public void robotInit() {
    	try {
			rgbFrame = new RGBImage();
		} catch (NIVisionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	frame = rgbFrame.image;
//        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
//        NIVision.IMAQdxConfigureGrab(session2);
    }

    public void operatorControl() {
        NIVision.IMAQdxStartAcquisition(session);
//        NIVision.IMAQdxStartAcquisition(session2);

        /**
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */
        NIVision.Rect rect = new NIVision.Rect(100, 100, 400, 400);

        while (isOperatorControl() && isEnabled()) {

//            takePic();
        	NIVision.IMAQdxGrab(session, frame, 1);
        	CameraServer.getInstance().setImage(frame);
//            NIVision.imaqDrawShapeOnImage(frame, frame, rect,
//                    DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 1.0f);
            

            /** robot code here! **/
            Timer.delay(0.005);		// wait for a motor update time
        }
        NIVision.IMAQdxStopAcquisition(session);
    }

    public void test() {
    }
    public void takePic(){
		try {
            NIVision.IMAQdxGrab(session, frame, 1);
            BinaryImage thresholdImage = rgbFrame.thresholdRGB(0, 45, 25, 255, 0, 47);   // keep only red objects
            BinaryImage bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);  // remove small artifacts
            BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
            CameraServer.getInstance().setImage(convexHullImage.image);
            //BinaryImage filteredImage = convexHullImage.particleFilter(cc);// find filled in rectangles
            
//            ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results
//            for (int i = 0; i < reports.length; i++) {                                // print results
//                ParticleAnalysisReport r = reports[i];
//                System.out.println("Particle: " + i + ":  Center of mass x: " + r.center_mass_x);
//            }
//            System.out.println(filteredImage.getNumberParticles() + "  " + Timer.getFPGATimestamp());

            /**
             * all images in Java must be freed after they are used since they are allocated out
             * of C data structures. Not calling free() will cause the memory to accumulate over
             * each pass of this loop.
             */
//            filteredImage.free();
            convexHullImage.free();
            bigObjectsImage.free();
            thresholdImage.free();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }
	}
}
