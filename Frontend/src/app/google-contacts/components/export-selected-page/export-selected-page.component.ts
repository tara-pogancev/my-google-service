import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import * as FileSaver from 'file-saver';
import { ExportService } from '../../services/export.service';

@Component({
  selector: 'export-selected-page',
  templateUrl: './export-selected-page.component.html',
  styleUrls: ['./export-selected-page.component.scss'],
})
export class ExportSelectedPageComponent implements OnInit {
  exportForm: FormGroup = new FormGroup({});

  constructor(
    private exportService: ExportService,
    @Inject(MAT_DIALOG_DATA) public data: number[]
  ) {}

  ngOnInit(): void {
    this.exportForm = new FormGroup({
      type: new FormControl('csv', {
        validators: [Validators.required],
      }),
    });
  }

  export() {
    if (this.exportForm.valid) {
      if (this.exportForm.controls.type.value == 'csv') {
        this.exportService.exportSelectedCsv(this.data).subscribe((res) => {
          FileSaver.saveAs(res.body, 'contacts.csv');
        });
      } else {
        this.exportService.exportSelectedJson(this.data).subscribe((res) => {
          FileSaver.saveAs(res.body, 'contacts.json');
        });
      }
    }
  }
}
