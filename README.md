# traffic_Simulator

## About

Traffic Simulator desktop java swing/console application made for Programming Technologies subject of the Computer Science degree at Complutense University of Madrid by Alberto Pastor & Ivan Fernandez.

The application manages three kind of simulation objects:
- Vehicles(default, car, bicycle)
- Roads(default, path, freeway)
- Junctions(default, congested junction, roundabout

You can find more information about how the simulation works in the [assignment's first requirements documentation](v4-slides.pdf)

## Options

`-f <file .ini path>` input `.ini` file for the simulation

`-o <file .ini path>` output `.ini` file for the simulation

`-s <number of simulation steps>` number of the steps for the simulation to execute.

`-h` shows every command available.

`-c < directory path >`  checks the output for every `.ini` found in the directory path specified.

`-d < directory path >` launchs the simulation for every `.ini` file in the directory path specified.

`-t < directory path >` launchs and tests the simulation for every `.ini` file in the directory path specified.

`-m < batch/gui >` launchs the application in console(batch) mode or gui mode (java swing).

If the output file command isn't inserted or the output file path isn't valid the application will print all the reports in console by default.

The command -s is optional, 10 will be the number of simulation steps by default.

Mode batch is set by default, use `-m gui` to launch swing version .

