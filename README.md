Number to String
================

## Problem Statement:

Using a JVM based language of choice, *produce a library* that has the following capability within it:

**given some integer (N) as an argument, return a string representation of that integer.**

##Analysis
String representation of a number in English language follows a few rules. 
 * From zero (0) to twenty (19) string representation is unique for each number i.e.
("Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
 "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
 "Eighteen", "Nineteen")
 * from 20 to 90 as multiple of 10 has uniques representation i.e. 
"Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety". 
 * Scales of ones, tens, hundreds, and thousands 
are increased by power of 10 and 
 * then larger numbers are constituted by 10 to the power 3 (10 cubed).   

Number | String Representation
-------|----------------------
1      |    One
10 | Ten
100 | Hundred
1,000 | Thousand
1,000,000 | Million
1,000,000,000 | Billion 
1,000,000,000,000 | Trillion

[Details](https://en.wikipedia.org/wiki/Long_and_short_scales#English-speaking)

Numbers can be negative and also contain decimal points. 
Decimal numbers are out of scope of this project.

Based on the english numbering system as stated above we can constitute below algorithm to
convert a number (non decimal) to string: 

### Algorithm
1. *Zero*
If the number is '0' then string representation is 'Zero' and no further processing is required
1. *Split in groups of three digit* numbers will be grouped by 3 digits (right to left). For example, 
   * 1st group (of 3 digits) represents values ones, tens and hundreds (1 to 999), 
   * 2nd group (of 3 digits) represents values thousands (1000 to 999,000)
   * 3rd group (of 3 digits) represents values millions (1,000,000 to 999,000,000)
   * continues similarly for billion, trillion and so forth
1. for each group apply below rules   
1. *Hundreds*
get the string representation of hundreds digit (3rd from right) / integer division by 100
from the list of unique numbers string values (one ... nineteen) corresponding to the integer 
digit and append the word Hundred. Ignore if the digit is '0'  
1. *Tens* 
if the digit  at tens is from 2 to 9 (2nd from right) / integer division by 10 to the remainder 
of hundreds rule then get the string representation of the number 20 to 90 string list. Ignore if the digit is '0'  
1. *Tens and Ones* 
If a number is in the range 1 to 19 use the unique number representation. 
1. *Combine groups*
if the number is split into multiple groups then combine the group's string representation
with corresponding scale in reverse order i.e. million then thousand and then hundred etc.
Symbols can be appended after the scale
   
#### Examples
 * input '0' :
   - String representation wil be 'Zero' in step 1
   - output = 'Zero'
 * input '43' :
   - not zero
   - group of 3 digit: 1 group i.e. 43
   - hundreds rule will be ignored
   - tens rule applies, string representation becomes Forty
   - tens and ones rule applies, appends Three
   - output = Forty Three
 * input '23443'  
   - non zero
   - split in 2 groups i.e. '443' and '23'
   - iterate each groups (as above example), string values 'Four Hundred Forty Three' and 'Twenty Three'
   - combine, reverse order with scale 'Twenty Three Thousand' then append 'Four Hundred Forty Three'
   - output: Twenty Three Thousand, Four Hundred Forty Three






