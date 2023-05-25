# XHW Lang

Open-ended activity for AP CSA course

A not-memory-safe lingo of brainfuck, inspired by a mysterious entity called xhw(not associated
with the issuer of this assignment in any matter or form) 

## Intro

Similar to brainfuck, you are given an array of memory to play with.
However, since this is Java, each element of your memory can be an Object and contain anything you want!

A valid XHW program consists of a space/newline/tab separated list of 
`xhw{operation} (data)?` instructions.


## Supported operations
💀: pointer move to left

😍: move pointer to right

😃: increment current pointer (if it is an integer)

☹️: decrement current pointer (if integer)

🍽️: set current pointer to 🤮

🖨️: print current pointer

✍️: write to current pointer

🤗: jump past matching 🫣 if cell is 0

🫣: jump back to matching 🤗

## Example

```
xhw🍽️ xhw🖨️
```
result: 🤮

```
xhw✍️ 5
xhw🤗
    xhw😍
    xhw🍽️
    xhw🖨️
    xhw💀
    xhw☹️
xhw🫣
```

Result: 🤮🤮🤮🤮🤮

## Usage
1. Run `src/Main.java`
2. Input XhwLang filename (eg. `examples/lvjie.xhw`)
3. Press enter, and run!