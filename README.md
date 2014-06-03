# inth

A Clojure library designed to scrape HTML pages for links and index them.

## Usage
Configure src/inth/db.clj, then: 

    $ lein uberjar

    $ java -jar inth.jar "http://news.ycombinator.com" "clojure" "deep learning" "cupcakes"

## License

Copyright © 2014 Erick S. Crager

Distributed under the Apache License, Version 2.0.
