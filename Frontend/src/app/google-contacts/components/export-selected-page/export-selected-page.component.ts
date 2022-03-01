import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'export-selected-page',
  templateUrl: './export-selected-page.component.html',
  styleUrls: ['./export-selected-page.component.scss'],
})
export class ExportSelectedPageComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: number[]) {}

  ngOnInit(): void {}

  //TODO Export selected contacts
}
