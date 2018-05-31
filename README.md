# traffic_Simulator

## About

Traffic Simulator desktop java swing/console application made for Programming Technologies subject of the Computer Science degree at Universidad Complutense Madrid by Alberto Pastor & Ivan Fernandez.

The application manages three kind of simulation objects:
- Vehicles(default, car, bicycle)
- Roads(default, path, freeway)
- Junctions(default, congested junction, roundabout
You can find more information about how the simulation works in the [assignment's first requirements documentation](v4-slides.pdf)

## Options

`-f <file .ini path> -o <file .ini path> -s <number of simulation steps>`

`-h`

`-c < directory path >`

`-d < directory path >`

`-t < directory path >`

`-m < batch/gui >`

If the output file command isn't inserted or the output file path isn't valid the application will print all the reports in console by default.

The command -s is optional, 10 will be the number of simulation steps by default.

Mode batch is set by default, use `-m gui` to launch swing version .

