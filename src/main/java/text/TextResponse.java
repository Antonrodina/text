package text;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Immutable
 *
 * @author antonio
 */
public class TextResponse {

    @JsonProperty("freq_word")
    private final String mostFrequentWord;

    @JsonProperty("avg_paragraph_size")
    private final double avgParagraphSize;

    @JsonProperty("avg_paragraph_processing_time")
    private final double avgParagraphProcessingTime;

    @JsonProperty("total_processing_time")
    private final long totalProcessingTime;

    @JsonCreator
    public TextResponse(@JsonProperty("freq_word") String mostFrequentWord,
                        @JsonProperty("avg_paragraph_size") double avgParagraphSize,
                        @JsonProperty("avg_paragraph_processing_time") double avgParagraphProcessingTime,
                        @JsonProperty("total_processing_time") long totalProcessingTime) {
        this.mostFrequentWord = mostFrequentWord;
        this.avgParagraphSize = avgParagraphSize;
        this.avgParagraphProcessingTime = avgParagraphProcessingTime;
        this.totalProcessingTime = totalProcessingTime;
    }

    public String getMostFrequentWord() {
        return mostFrequentWord;
    }

    public double getAvgParagraphSize() {
        return avgParagraphSize;
    }

    public double getAvgParagraphProcessingTime() {
        return avgParagraphProcessingTime;
    }

    public long getTotalProcessingTime() {
        return totalProcessingTime;
    }

    @Override
    public String toString() {
        return "TextResponse{" +
                "mostFrequentWord='" + mostFrequentWord + '\'' +
                ", avgParagraphSize=" + avgParagraphSize +
                ", avgParagraphProcessingTime=" + avgParagraphProcessingTime +
                ", totalProcessingTime=" + totalProcessingTime +
                '}';
    }
}
