package com.hessky.oosc.lab1;

//public class JavaFxVideoApp extends Application {
//    static {
//        System.load("C:\\Users\\zandr\\IdeaProjects\\oosc\\src\\main\\resources\\opencv_java320.dll");
//    }
//    private static int cameraId = 0;
//    private VideoCapture videoCapture = new VideoCapture();
//    private ScheduledExecutorService timer;
//    private boolean cameraActive = false;
//
//    public static Image mat2Image(Mat frame) {
//        try {
//            return SwingFXUtils.toFXImage(matToBufferedImage(frame), null);
//        }
//        catch (Exception e) {
//            System.err.println("Cannot convert the Mat obejct: " + e);
//            return null;
//        }
//    }
//
//    /**
//     * Generic method for putting element running on a non-JavaFX thread on the
//     * JavaFX thread, to properly update the UI
//     *
//     * @param property a {@link ObjectProperty}
//     * @param value    the value to set for the given {@link ObjectProperty}
//     */
//    public static <T> void onFXThread(final ObjectProperty<T> property, final T value) {
//        Platform.runLater(() -> {
//            property.set(value);
//        });
//    }
//
//    private static BufferedImage matToBufferedImage(Mat original) {
//        // init
//        BufferedImage image = null;
//        int width = original.width(), height = original.height(), channels = original.channels();
//        byte[] sourcePixels = new byte[width * height * channels];
//        original.get(0, 0, sourcePixels);
//
//        if (original.channels() > 1) {
//            image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
//        } else {
//            image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
//        }
//        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
//        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
//
//        return image;
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        BorderPane pane = new BorderPane();
//        pane.setPrefSize(500, 500);
//        ImageView imageView = new ImageView();
//        Button button = new Button();
//        button.setOnAction(e -> {
//            if (!cameraActive) {
//                videoCapture.open(cameraId);
//                if (videoCapture.isOpened()) {
//                    cameraActive = true;
//                    Runnable frameGrabber = () -> {
//                        Mat frame = grabFrame();
//                        Image imageToShow = mat2Image(frame);
//                        updateImageView(imageView, imageToShow);
//                    };
//                    timer = Executors.newSingleThreadScheduledExecutor();
//                    timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
//                    button.setText("stop camera");
//                } else {
//                    System.err.println("Impossible to open the camera connection...");
//                }
//            } else {
//                this.cameraActive = false;
//                // update again the button content
//                button.setText("Start Camera");
//
//                // stop the timer
//                this.stopAcquisition();
//            }
//
//        });
//        pane.setCenter(imageView);
//        pane.setBottom(button);
//        Scene scene = new Scene(pane);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    private Mat grabFrame() {
//        Mat frame = new Mat();
//        if (videoCapture.isOpened()) {
//            try {
//                videoCapture.read(frame);
//                if (!frame.empty()) {
//                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return frame;
//    }
//
//    private void stopAcquisition() {
//        if (this.timer != null && !this.timer.isShutdown()) {
//            try {
//                // stop the timer
//                this.timer.shutdown();
//                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
//            }
//            catch (InterruptedException e) {
//                // log any exception
//                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
//            }
//        }
//
//        if (videoCapture.isOpened()) {
//            // release the camera
//            videoCapture.release();
//        }
//    }
//
//    private void updateImageView(ImageView view, Image image) {
//        onFXThread(view.imageProperty(), image);
//    }
//}
