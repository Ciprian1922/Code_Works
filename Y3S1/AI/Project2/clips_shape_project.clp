
(deftemplate square
   (slot id)
   (slot side-length))

(deftemplate rectangle
   (slot id)
   (slot width)
   (slot height))

(deftemplate circle
   (slot id)
   (slot radius))

(deffacts test-8-8
   (square (id A) (side-length 3))
   (square (id B) (side-length 5))
   (rectangle (id C) (width 5) (height 7))
   (circle (id D) (radius 2))
   (circle (id E) (radius 6)))

(defrule calculate-square-area
   ?square <- (square (id ?id) (side-length ?length))
   =>
   (bind ?area (* ?length ?length))
   (printout t "Square " ?id " Area: " ?area crlf))

(defrule calculate-rectangle-area
   ?rectangle <- (rectangle (id ?id) (width ?width) (height ?height))
   =>
   (bind ?area (* ?width ?height))
   (printout t "Rectangle " ?id " Area: " ?area crlf))

(defrule calculate-circle-area
   ?circle <- (circle (id ?id) (radius ?radius))
   =>
   (bind ?area (* 3.14159 ?radius ?radius))  ; Using pi = 3.14159
   (printout t "Circle " ?id " Area: " ?area crlf))

(defrule calculate-square-perimeter
   ?square <- (square (id ?id) (side-length ?length))
   =>
   (bind ?perimeter (* 4 ?length))
   (printout t "Square " ?id " Perimeter: " ?perimeter crlf))

(defrule calculate-rectangle-perimeter
   ?rectangle <- (rectangle (id ?id) (width ?width) (height ?height))
   =>
   (bind ?perimeter (+ (* 2 ?width) (* 2 ?height)))
   (printout t "Rectangle " ?id " Perimeter: " ?perimeter crlf))

(defrule calculate-circle-perimeter
   ?circle <- (circle (id ?id) (radius ?radius))
   =>
   (bind ?perimeter (* 2 3.14159 ?radius))  ; Using pi = 3.14159
   (printout t "Circle " ?id " Perimeter: " ?perimeter crlf))

; Run the facts and rules
(reset)
(run)
