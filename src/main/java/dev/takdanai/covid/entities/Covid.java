package dev.takdanai.covid.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "province",
        "new_case",
        "new_death",
        "total_case",
        "total_death",
        "new_case_excludeabroad",
        "total_case_excludeabroad",
        "year",
        "weeknum",
        "update_date"
})
public class Covid {

    private Integer year;
    private Integer week;
    private String province;
    private Integer newCase;
    private Integer totalCase;
    private Integer newCaseExcludeAboard;
    private Integer totalCaseExcludeAboard;
    private Integer newDeath;
    private Integer totalDead;
    private String updateDate;

    @JsonCreator
    public Covid(
            @JsonProperty("year") Integer year,
            @JsonProperty("weeknum") Integer week,
            @JsonProperty("province") String province,
            @JsonProperty("new_case") Integer newCase,
            @JsonProperty("total_case") Integer totalCase,
            @JsonProperty("new_case_excludeabroad") Integer newCaseExcludeAboard,
            @JsonProperty("total_case_excludeabroad") Integer totalCaseExcludeAboard,
            @JsonProperty("new_death") Integer newDeath,
            @JsonProperty("total_death") Integer totalDead,
            @JsonProperty("update_date") String updateDate
    ) {
        this.year = year;
        this.week = week;
        this.province = province;
        this.newCase = newCase;
        this.totalCase = totalCase;
        this.newCaseExcludeAboard = newCaseExcludeAboard;
        this.totalCaseExcludeAboard = totalCaseExcludeAboard;
        this.newDeath = newDeath;
        this.totalDead = totalDead;
        this.updateDate = updateDate;
    }
}
