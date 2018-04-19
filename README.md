Run application with `gradle run`. It listens to 8080, e.g. `localhost:8080/betvictor/text?p_start=1&p_end=1&w_count_min=1&w_count_max=1`

Some clarifications:
-Specs doesn't state any restriction to allowed parameter values for the endpoint /betvictor/text, so we allow all positives
-"total_processing_time" is in millis
-When computing the most frequent word, in case of tie takes the last to appear for the first time