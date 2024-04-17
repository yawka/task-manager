Object.defineProperty(exports, "__esModule", { value: true });

export let projectNewData = [];
export function updateProjectNewData(newData) {
    projectNewData.splice(0, projectNewData.length, ...newData.map(task => ({
        ...task,
        startDate: new Date(task.startDate),
        endDate: new Date(task.endDate)
    })));
}

export let editingResources = [
    { resourceId: 1, resourceName: "Martin Tamer" },
    { resourceId: 2, resourceName: "Rose Fuller" },
    { resourceId: 3, resourceName: "Margaret Buchanan" },
    { resourceId: 4, resourceName: "Fuller King" },
    { resourceId: 5, resourceName: "Davolio Fuller" },
    { resourceId: 6, resourceName: "Van Jack" },
    { resourceId: 7, resourceName: "Fuller Buchanan" },
    { resourceId: 8, resourceName: "Jack Davolio" },
    { resourceId: 9, resourceName: "Tamer Vinet" },
    { resourceId: 10, resourceName: "Vinet Fuller" },
    { resourceId: 11, resourceName: "Bergs Anton" },
    { resourceId: 12, resourceName: "Construction Supervisor" },
];

