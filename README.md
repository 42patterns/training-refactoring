Refactoring
===========

Task
----

Analize the dictionary code and extend it with the following features

* Additional command: delete [position] and delete all
* Additional dictionary source from new onet.pl dictionary. Extend the search command to search [onet|dict] [word]
* Printouts in HTML format

Steps
-----

1. Extract Method
> Extract method like printInfo(), savedWords() 
> Move local to instance variables (list1, list2)

2. Increase readability: change names of class, method or attributes
> Renaming internal lists, introduce WebDictionary object instead of structural code

3. Adding model
> Introduce test based on extending original object (and changing from private to protected)
> Introduce builder pattern in model, toString w WebDictionary i moveMethod - to model

4. TDD - stateful end-to-end test 
> Add test based on protected method scope
> Add characteristic test - for massive check

5. Extract local variable
> From a chained expression extract local variable temp
