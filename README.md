Reagent
=======

An experiment which rewrites RxJava-like types using polymorphism (and also in Kotlin).

Just like how every `List<T>` and `Set<T>` is a `Collection<T>`, every `One<T>` is just a `Many<T>`
with only a single element, etc.

Missing:

 * `Many.flatMap*` implementations
 * Disposing



Type Hierarchy
--------------

              +---------------+
              |               |
              |    Many<T>    |
              |               |
              +---------------+
                      ^
                      |
              +-------+-------+
              |               |
              |    Maybe<T>   |
              |               |
              +---------------+
                 ^         ^
                 |         |
    +------------+--+   +--+-----------+
    |               |   |              |
    |    One<T>     |   |     Task     |
    |               |   |              |
    +---------------+   +--------------+



FAQ
---

### Should I use this?

No.



License
-------

    Copyright 2016 Jake Wharton
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
