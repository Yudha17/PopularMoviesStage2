package com.example.slametsudarsono.popularmoviesstage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

        @SerializedName("id")
        private Integer id;
        @SerializedName("page")
        private Integer page;
        @SerializedName("results")
        private List<Review> results = null;
        @SerializedName("total_pages")
        private Integer totalPages;
        @SerializedName("total_results")
        private Integer totalResults;

        public ReviewResponse(Integer id, Integer page, List<Review> results, Integer totalPages, Integer totalResults) {
            this.id = id;
            this.page = page;
            this.results = results;
            this.totalPages = totalPages;
            this.totalResults = totalResults;
        }

        public ReviewResponse(){}

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public List<Review> getResults() {
            return results;
        }

        public void setResults(List<Review> results) {
            this.results = results;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Integer getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(Integer totalResults) {
            this.totalResults = totalResults;
        }

}

