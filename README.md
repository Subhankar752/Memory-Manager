# Memory-Manager
 memory manager for a new multitasking OS

 You are given a task to design a memory manager for a new multitasking OS to support the following features:

A process may request N blocks of memory at any point that it is alive
A process may free previously allocated blocks of memory at any point in its lifetime
A process may request N contiguous blocks of memory. Fail if unavailable.
A process may not request more than a fraction of the total memory: 25%

Bonus Question:
Fixed Size Block Allocation: A process can request N blocks of memory but it can be given in fixed size blocks. The size of the block needs to be taken as input.
Garbage Collection: Any variables older than time T secs, would be cleaned up. The time duration needs to be taken as input.

Input Format:
<total-block-count>
<command-1> <process> <args>
:
<command-N> <process> <args>

Keep in mind that your inputs can vary in size - from 1 to N, N <= 106

The available commands & their formats are described below:
allocate: allocate <process> <variable> <blocks-requested> <require-contiguous-flag>
free: free <process> <variable>
kill: kill <process>
inspect: inspect <process>

Output Format:
<command-result> <allocated-space-block-count> / <free-space-block-count>

Example:

100
allocate P1 var_x 1000 false
error 0 / 100
allocate P1 var_x 10 false
success 10 / 90
allocate P2 var_y 25 true
success 35 / 65
free P1 var_x
success 25 / 75
kill P2
success 0 / 100
allocate P1 var_z 10 true
success 10 / 90
allocate P4 var_x 5 true
success 15 / 85
allocate P1 var_w 5 true
success 20 / 80
free P4 var_x
success 15 / 85
allocate P1 var_y 6 false
success 21 / 79
inspect P1
var_z 0-9
var_w 15-19
var_y 10-14 20-20
