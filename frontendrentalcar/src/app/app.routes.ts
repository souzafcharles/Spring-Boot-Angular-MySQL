import { Routes } from '@angular/router';
import { CarlistComponent } from './components/carlist/carlist.component';

export const routes: Routes = [
    {path:"", redirectTo:"dashboard", pathMatch:"full"},
    {path:"car", component: CarlistComponent}
];
