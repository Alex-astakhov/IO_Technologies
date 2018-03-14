package dataModels;

import enums.ArticleMetrics;

import java.util.Date;

public class ArticleModel {
    private int pageViews;
    private Date averageTime;
    private int finishedReading;
    private int recirculation;

    public ArticleModel(int pageViews, Date averageTime, int finishedReading, int recirculation) {
        this.pageViews = pageViews;
        this.averageTime = averageTime;
        this.finishedReading = finishedReading;
        this.recirculation = recirculation;
    }

    public int compareByMetric(ArticleModel otherArticle, ArticleMetrics metric) {
        switch (metric){
            case PAGEVIEWS:
                return Integer.compare(this.pageViews, otherArticle.pageViews);
            case AVERAGE_TIME:
                return this.averageTime.compareTo(otherArticle.averageTime);
            case RECIRCULATION:
                return Integer.compare(this.recirculation, otherArticle.recirculation);
            case FINISHED_READING:
                return Integer.compare(this.finishedReading, otherArticle.finishedReading);
        }
        throw new EnumConstantNotPresentException(ArticleMetrics.class, "Unknown metric!");
    }

    @Override
    public String toString() {
        return "ArticleModel{" +
                "pageViews=" + pageViews +
                ", averageTime=" + averageTime +
                ", finishedReading=" + finishedReading +
                ", recirculation=" + recirculation +
                '}';
    }
}
