package superp.techpark.ru.lesson5;

interface ProgressListener {
    void onProgressUpdate(int progress);

    void onFinish();
}
