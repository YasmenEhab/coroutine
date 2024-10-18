task 5 collect and collectlatest
collect and collectLatest are both terminal operators in Kotlin's Flow API that allow you to collect emitted values from a flow
When you use collect, it collects all values emitted by the flow, and it processes each value in the order they are emitted. If the collector is busy processing one value, any new emissions will wait until the current processing completes before they are collected and processed.
When you use collectLatest, it only collects the latest emitted value and cancels the processing of any previous value if a new value is emitted before the current one is finished. In other words, it will drop the previous value and start processing the new one.
